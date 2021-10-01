// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveJoystickCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class RobotContainer {
  private final DriveTrainSubsystem m_driveTrainSubsystem = new DriveTrainSubsystem();
  private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();

  private final Joystick m_stick = new Joystick(0);

  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public RobotContainer() {
    configureButtonBindings();

    m_driveTrainSubsystem.setDefaultCommand(new DriveJoystickCommand(m_driveTrainSubsystem, m_stick));
  }

  private void configureButtonBindings() {
    JoystickButton climbButton = new JoystickButton(m_stick, 4);
    JoystickButton climbSlowButton = new JoystickButton(m_stick, 6);
    JoystickButton resetClimbButton = new JoystickButton(m_stick, 12);

 }

  public Command getAutonomousCommand() {
    return null;
  }
}
