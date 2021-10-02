package frc.robot.commands;

import frc.robot.subsystems.PullSubsystem;

public class PullCommand extends MotorCommandBase {
	public PullCommand(PullSubsystem subsystem, boolean reverse) {
		super(subsystem, reverse ? 0.6 : -0.6);
	}
}
