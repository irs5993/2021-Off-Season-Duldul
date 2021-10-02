package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ResetClimbCommand extends MotorCommandBase {
	public ResetClimbCommand(ClimbSubsystem subsystem) {
		super(subsystem, 0.4);
	}
}