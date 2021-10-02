package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends MotorCommandBase {
	public ClimbCommand(ClimbSubsystem subsystem, boolean fast) {
		super(subsystem, fast ? 1.0 : 0.6);
	}
}