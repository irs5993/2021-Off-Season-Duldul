package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ResetClimbCommand extends MotorCommandBase {
	public ResetClimbCommand(ClimbSubsystem subsystem, double power, double duration) {
		super(subsystem, power, duration);
	}
}