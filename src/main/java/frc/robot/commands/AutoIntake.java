// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.PullSubsystem;

public class AutoIntake extends CommandBase {
  private final DriveTrainSubsystem m_driveTrain;
  private final PullSubsystem m_pull;

  private NetworkTable table;
  double[] defaultValue = {};

  private Joystick m_stick;
  private final boolean isManual;
 
  public AutoIntake(DriveTrainSubsystem driveTrain, PullSubsystem pull, boolean manual) {
    m_driveTrain = driveTrain;
    m_pull = pull;

    isManual = manual;
    if (isManual) {
      m_stick = new Joystick(0);
    }    

    addRequirements(m_driveTrain, m_pull);
  }
  
  @Override
  public void initialize() {
    table = NetworkTableInstance.getDefault().getTable("GRIP/Powercell");
  }

  @Override
  public void execute() {
    double[] xPositions = table.getEntry("x").getDoubleArray(defaultValue);
    double[] yPositions = table.getEntry("y").getDoubleArray(defaultValue);
    double[] sizes = table.getEntry("size").getDoubleArray(defaultValue);

    double closest = 0;
    int winner = -1;
    for(int i = 0; i < sizes.length; i++) {
      if (sizes[i] > closest) {
        winner = i;
        closest = sizes[i];
      }
    }

    if(winner != -1 && winner <= xPositions.length - 1) {
      // There is a target
      double x = xPositions[winner];
    
      double yRotation = 0;
      double xSpeed = isManual ? m_stick.getY() : 0.7;

      x = map(x, 0, 160, -80, 80);
      yRotation = map(x, -80, 80, -0.9, 0.9);

      if (yRotation < 0) {
        yRotation = Math.min(yRotation, -0.45);
      } else {
        yRotation = Math.max(yRotation, 0.45);
      }

      if (!isManual) {
        m_pull.setSpeed(0.36);
      }

      m_driveTrain.arcadeDrive(xSpeed, yRotation);

    } else {
      if (!isManual) {
        m_driveTrain.arcadeDrive(0.65, 0);   
        m_pull.setSpeed(0.36);
      } else {
        m_pull.setSpeed(0);

      }
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
