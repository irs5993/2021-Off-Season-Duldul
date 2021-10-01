// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMVictorSPX;

public class ClimbSubsystem extends SubsystemBase {
  /** Creates a new ClimbSubsystem. */
  private final PWMVictorSPX m_motorLeft;
  private final PWMVictorSPX m_motorRight;

  public ClimbSubsystem() {
    m_motorLeft = new PWMVictorSPX(4);
    m_motorRight = new PWMVictorSPX(5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed) {
    m_motorLeft.set(speed);
    m_motorRight.set(-speed);
  }
}
