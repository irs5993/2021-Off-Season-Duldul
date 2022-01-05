// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoIntake;
import frc.robot.commands.LockTarget;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DriveJoystickCommand;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.PullCommand;
import frc.robot.commands.ResetClimbCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.helpers.ChasisControl;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.PullSubsystem;
import frc.robot.subsystems.ShootSubsystem;



public class RobotContainer {
  private final DriveTrainSubsystem m_driveTrainSubsystem = new DriveTrainSubsystem();
  private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();
  private final ShootSubsystem m_shootSubsystem = new ShootSubsystem();
  private final PullSubsystem m_pullSubsystem = new PullSubsystem();

  private final Joystick m_stick = new Joystick(Constants.JOYSTICK);

  public RobotContainer() {
    configureButtonBindings();
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);

    m_driveTrainSubsystem.setDefaultCommand(new DriveJoystickCommand(m_driveTrainSubsystem, m_stick));

    
  }

  

  private void configureButtonBindings() {
    // ---------------------------------------------------------------------
    // Creating Button References 
    JoystickButton climbButton = new JoystickButton(m_stick, Constants.JoystickButtons.Climb.NORMAL);
    JoystickButton climbSlowButton = new JoystickButton(m_stick, Constants.JoystickButtons.Climb.SLOW);
    JoystickButton resetClimbButton = new JoystickButton(m_stick, Constants.JoystickButtons.Climb.RESET);
    JoystickButton shootButton = new JoystickButton(m_stick, Constants.JoystickButtons.Shoot.NORMAL);
    JoystickButton pullButton = new JoystickButton(m_stick, Constants.JoystickButtons.Pull.NORMAL);
    JoystickButton pullReverseButton = new JoystickButton(m_stick, Constants.JoystickButtons.Pull.REVERSE);
    JoystickButton preventJamming = new JoystickButton(m_stick, Constants.JoystickButtons.Pull.PREVENT_JAM);
    JoystickButton pullFastButton = new JoystickButton(m_stick, Constants.JoystickButtons.Pull.NORMAL_FAST);
    JoystickButton pullFastReverseButton = new JoystickButton(m_stick, Constants.JoystickButtons.Pull.REVERSE_FAST);
    JoystickButton autoIntake = new JoystickButton(m_stick, 9);
    // ---------------------------------------------------------------------


    // ---------------------------------------------------------------------
    // Binding Commands to Buttons
    climbButton.toggleWhenActive(new ClimbCommand(m_climbSubsystem, -1, 0));
    climbSlowButton.whileHeld(new ClimbCommand(m_climbSubsystem, -0.5, 0));

    pullButton.whileHeld(new PullCommand(m_pullSubsystem, -0.3, 0));
    pullReverseButton.whileHeld(new PullCommand(m_pullSubsystem, 0.3, 0));

    pullFastButton.whileHeld(new PullCommand(m_pullSubsystem, -0.7, 0));
    pullFastReverseButton.whileHeld(new PullCommand(m_pullSubsystem, 0.7, 0));

    resetClimbButton.whileHeld(new ResetClimbCommand(m_climbSubsystem, 0.4, 0));
    shootButton.whileHeld(new ShootCommand(m_shootSubsystem, 1, 0));
    preventJamming.whileHeld(new ParallelCommandGroup(
      new PullCommand(m_pullSubsystem, 0.3, 0),
      new ShootCommand(m_shootSubsystem, -0.2, 0)
    ));

    autoIntake.whileHeld(new LockTarget(m_driveTrainSubsystem, m_pullSubsystem, m_shootSubsystem));
    //autoIntake.whileHeld(new AutoIntake(m_driveTrainSubsystem, m_pullSubsystem, true));

    // ---------------------------------------------------------------------
 }
 
  public Command getAutonomousCommand() {
    // -1 ileri, 1 geri
    return new SequentialCommandGroup(
        new ParallelCommandGroup(
          new SequentialCommandGroup(
            new DriveManualCommand(m_driveTrainSubsystem, new ChasisControl(1, 0, 0.5)),
            new DriveManualCommand(m_driveTrainSubsystem, new ChasisControl(-1, 0, 0.4))
        ),

        new ParallelCommandGroup(
          new ShootCommand(m_shootSubsystem, 0.6, 8),

          new SequentialCommandGroup(
            new WaitCommand(3.5), 
            new PullCommand(m_pullSubsystem, 0.24, 3.5)
          )
        )
        
      ),
      
      new AutoIntake(m_driveTrainSubsystem, m_pullSubsystem, false)
    );
  }
}
