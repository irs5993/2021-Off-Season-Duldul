package frc.robot.commands;

import frc.robot.subsystems.PullSubsystem;

public class PullCommand extends MotorCommandBase {
	public PullCommand(PullSubsystem subsystem, double power) {
		super(subsystem, power);
	}
}
