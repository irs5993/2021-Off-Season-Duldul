// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorSubsystemBase extends SubsystemBase {
  private final PWMVictorSPX m_leftMotor;
  private final PWMVictorSPX m_rightMotor;

  public MotorSubsystemBase(int leftMotor, int rightMotor) {
    m_leftMotor = new PWMVictorSPX(leftMotor);
    m_rightMotor = new PWMVictorSPX(rightMotor);
  }

  public void setSpeed(double speed) {
    m_leftMotor.set(speed);
    m_rightMotor.set(-speed);
  }
}
