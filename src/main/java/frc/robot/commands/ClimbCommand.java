package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends MotorCommandBase {
	public ClimbCommand(ClimbSubsystem subsystem, double power, double duration) {
		super(subsystem, power, duration);
	}
}