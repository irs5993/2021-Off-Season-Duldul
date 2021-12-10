// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveJoystickCommand extends CommandBase {
  private final DriveTrainSubsystem m_driveTrain;
  private final Joystick m_stick;

  public DriveJoystickCommand(DriveTrainSubsystem driveTrain, Joystick stick) {
    m_driveTrain = driveTrain;
    m_stick = stick;
    addRequirements(m_driveTrain);
  }

  @Override
  public void execute() {
    // Reading controller values
    double slider = m_stick.getRawAxis(3);
    int POV = m_stick.getPOV(0);

    double multiplier = map(slider, -1, 1, 1, 0.3);
    double speed = m_stick.getY() * multiplier;
    double rotation = m_stick.getZ() * multiplier;

    // Minimal adjustments using the POV on the controller    
    if (POV != -1) {
      if (POV > 0 && POV < 180) {
        rotation = 0.55;
      } else if (POV > 180 && POV < 360) {
        rotation = -0.55;
      }
    }

    m_driveTrain.arcadeDrive(speed, rotation);
  }

  // Mapping function to change the range of a value while keeping the ratio 
  private static double map(double value, double old_min, double old_max, double new_min, double new_max) {
    return (((value - old_min) * (new_max - new_min)) / (old_max - old_min)) + new_min;
  }

  @Override
  public void end(boolean interrupted) {
    m_driveTrain.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}