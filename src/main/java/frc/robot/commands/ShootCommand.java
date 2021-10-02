package frc.robot.commands;

import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends MotorCommandBase {
	public ShootCommand(ShootSubsystem subsystem, boolean reverse) {
		super(subsystem, reverse ? -0.2 : 1);
	}
}
