// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.ChasisControl;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveManualCommand extends CommandBase {
  private final Timer m_timer;
  private final DriveTrainSubsystem m_driveTrain;
  private final ChasisControl m_control;

  public DriveManualCommand(DriveTrainSubsystem driveTrain, ChasisControl control) {
    m_driveTrain = driveTrain;
    m_control = control;
    m_timer = new Timer();
    addRequirements(m_driveTrain);
    
  }

  @Override
  public void initialize() {
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void execute() {
    m_driveTrain.arcadeDrive(m_control.speed, m_control.rotation);
  }

  @Override
  public void end(boolean interrupted) {
    m_driveTrain.stop();
    m_timer.stop();
  }

  @Override
  public boolean isFinished() {
    return m_timer.hasElapsed(m_control.duration);
  }
}