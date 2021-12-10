package frc.robot.commands;

import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends MotorCommandBase {
	public ShootCommand(ShootSubsystem subsystem, double power, double duration) {
		super(subsystem, power, duration);
	}
}
