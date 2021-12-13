// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MotorSubsystemBase;

public class MotorCommandBase extends CommandBase {
  private final MotorSubsystemBase m_speedSubsystem;
  private Timer m_timer;

  private final double m_speed;

  private final boolean isAutonomous;
  private double m_command_duration;

  public MotorCommandBase(MotorSubsystemBase speedSubsystem, double speed, double duration) {
    isAutonomous = duration > 0;
    if (isAutonomous) {
      m_timer = new Timer();
      m_command_duration = duration;
    }
    

    m_speedSubsystem = speedSubsystem;
    m_speed = speed;
   
    addRequirements(m_speedSubsystem);
  }

  @Override
  public void initialize() {
    if (isAutonomous) {
      m_timer.reset();
      m_timer.start();
    }
  }

  @Override
  public void execute() {
    m_speedSubsystem.setSpeed(m_speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_speedSubsystem.setSpeed(0.0);
  }

  @Override
  public boolean isFinished() {
    if (isAutonomous) {
      return m_timer.hasElapsed(m_command_duration);
    } else {
      return false;
    }
  }
}
