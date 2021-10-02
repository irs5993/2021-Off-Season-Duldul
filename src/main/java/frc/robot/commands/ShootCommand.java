package frc.robot.commands;

import frc.robot.subsystems.ShootSubsystem;

public class ShootCommand extends MotorCommandBase {
	public ShootCommand(ShootSubsystem subsystem) {
		super(subsystem, 1.0);
	}
}
