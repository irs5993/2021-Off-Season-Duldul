package frc.robot.commands;

import frc.robot.subsystems.PullSubsystem;

public class PullCommand extends MotorCommandBase {
	public PullCommand(PullSubsystem subsystem, double power, double duration) {
		super(subsystem, power, duration);
	}
}
