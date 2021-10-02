package frc.robot.commands;

import frc.robot.subsystems.PullSubsystem;

public class PullCommand extends MotorCommandBase {
	public PullCommand(PullSubsystem subsystem) {
		super(subsystem, -0.6);
	}
}
