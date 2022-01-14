// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.PullSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class LockTarget extends CommandBase {
  private final DriveTrainSubsystem m_driveTrain;
  private final PullSubsystem m_pull;
  private final ShootSubsystem m_shoot;
  private final Command ShooterCommand;

  private NetworkTable table;
  double[] defaultValue = {};

  private final double MinRotationVoltage = 0.45;
  private final double MaxRotationVoltage = 0.78;

  private final int ImageWidthPixels = 160;
  private final int CenterAllowance = 8;

  private final double YMin = 50;
  private final double YMax = 85;

  private final double YTarget = 60;
  private final double YThreshold = 3;

  public LockTarget(DriveTrainSubsystem driveTrain, PullSubsystem pull, ShootSubsystem shoot) {
    m_driveTrain = driveTrain;
    m_pull = pull;
    m_shoot = shoot;

    ShooterCommand = new ParallelCommandGroup(
      new ShootCommand(m_shoot, 0.6, 8),

      new SequentialCommandGroup(
          new WaitCommand(3.5), 
          new PullCommand(m_pull, 0.5, 3.5)
      )
    ); 

    addRequirements(m_driveTrain, m_pull, m_shoot);
  }
  
  @Override
  public void initialize() {
    table = NetworkTableInstance.getDefault().getTable("GRIP/Target");
  }

  @Override
  public void execute() {
    double[] xPositions = table.getEntry("centerX").getDoubleArray(defaultValue);
    double[] yPositions = table.getEntry("centerY").getDoubleArray(defaultValue);
    //double[] sizes = table.getEntry("size").getDoubleArray(defaultValue);

    // Target is clear
    if (xPositions.length == 1) {
        SmartDashboard.putBoolean("Target Found", true);

        double x = xPositions[0];
        double y = yPositions[0];
        x = map(x, 0, ImageWidthPixels, -ImageWidthPixels/2, ImageWidthPixels/2);

        double yRotation = 0;
        double xSpeed = 0;

        if (x >= -CenterAllowance && x <= CenterAllowance) {
            // Locked to target, stop the rotation.
            
            if (y >= YTarget) {
              xSpeed = map(y, YTarget, YMax, -0.4, -0.9);
            } else if (y < YTarget) {
              xSpeed = map(y, YMin, YTarget, 0.9, 0.4);
            }                      
        } else {
            // Yet to be locked on target, centering...
            yRotation = map(x, -ImageWidthPixels/2, ImageWidthPixels/2, -MaxRotationVoltage, MaxRotationVoltage);
            if (yRotation < 0) {
                yRotation = Math.min(yRotation, -MinRotationVoltage);
            } else {
                yRotation = Math.max(yRotation, MinRotationVoltage);
            }
        }

        SmartDashboard.putNumber("X Speed", xSpeed);
        SmartDashboard.putNumber("Y Rotation", yRotation);

        m_driveTrain.arcadeDrive(xSpeed, yRotation);
    } else {
      SmartDashboard.putBoolean("Target Found", false);
    }
  }

  private static double map(double value, double old_min, double old_max, double new_min, double new_max) {
    return (((value - old_min) * (new_max - new_min)) / (old_max - old_min)) + new_min;
  }

  @Override
  public void end(boolean interrupted) {
    m_pull.setSpeed(0);
    m_driveTrain.arcadeDrive(0, 0);

    CommandScheduler.getInstance().schedule(ShooterCommand);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
