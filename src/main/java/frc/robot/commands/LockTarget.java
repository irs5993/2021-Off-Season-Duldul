// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
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

  private NetworkTable table;
  double[] defaultValue = {};

  private final double MinRotationVoltage = 0.3;
  private final double MaxRotationVoltage = 0.8;

  private final int ImageWidthPixels = 160;
  private final int CenterAllowance = 20;

  public LockTarget(DriveTrainSubsystem driveTrain, PullSubsystem pull, ShootSubsystem shoot) {
    m_driveTrain = driveTrain;
    m_pull = pull;
    m_shoot = shoot;

    addRequirements(m_driveTrain, m_pull, m_shoot);
  }
  
  @Override
  public void initialize() {
    table = NetworkTableInstance.getDefault().getTable("GRIP/Target");
  }

  @Override
  public void execute() {
    double[] xPositions = table.getEntry("x").getDoubleArray(defaultValue);
    double[] yPositions = table.getEntry("y").getDoubleArray(defaultValue);
    //double[] sizes = table.getEntry("size").getDoubleArray(defaultValue);

    // Target is clear
    if (xPositions.length == 1) {
        double x = xPositions[0];
        x = map(x, 0, ImageWidthPixels, -ImageWidthPixels/2, ImageWidthPixels/2);

        double yRotation = 0;
        if (x >= -CenterAllowance && x <= CenterAllowance) {
            // Locked to target, stop the rotation.
            new ParallelCommandGroup(
                new ShootCommand(m_shoot, 0.6, 8),

                new SequentialCommandGroup(
                    new WaitCommand(3.5), 
                    new PullCommand(m_pull, 0.24, 3.5)
                )
            );
            //new ScheduleCommand(new PullCommand(m_pull, 0.24, 0));
        } else {
            // Yet to be locked on target, centering...
            yRotation = map(x, -ImageWidthPixels/2, ImageWidthPixels/2, -MaxRotationVoltage, MaxRotationVoltage);
            if (yRotation < 0) {
                yRotation = Math.min(yRotation, -MinRotationVoltage);
            } else {
                yRotation = Math.max(yRotation, MinRotationVoltage);
            }
        }

        m_driveTrain.arcadeDrive(0, yRotation);
    }
  }

  private static double map(double value, double old_min, double old_max, double new_min, double new_max) {
    return (((value - old_min) * (new_max - new_min)) / (old_max - old_min)) + new_min;
  }

  @Override
  public void end(boolean interrupted) {
    m_pull.setSpeed(0);
    m_driveTrain.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
