// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MotorSubsystemBase;

public class MotorCommandBase extends CommandBase {
  private final MotorSubsystemBase m_speedSubsystem;
  private final double m_speed;

  /** Creates a new ClimbCommand. */
  public MotorCommandBase(MotorSubsystemBase speedSubsystem, double speed) {
    m_speedSubsystem = speedSubsystem;
    m_speed = speed;
    addRequirements(m_speedSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_speedSubsystem.setSpeed(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_speedSubsystem.setSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
