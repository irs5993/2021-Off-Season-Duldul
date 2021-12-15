// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.PullSubsystem;

public class AutoIntake extends CommandBase {
  private final DriveTrainSubsystem m_driveTrain;
  private final PullSubsystem m_pull;

  private NetworkTable table;
  double[] defaultValue = new double[0];
 
  public AutoIntake(DriveTrainSubsystem driveTrain, PullSubsystem pull) {
    m_driveTrain = driveTrain;
    m_pull = pull;

    addRequirements(m_driveTrain, m_pull);
  }
  
  @Override
  public void initialize() {
    table = NetworkTableInstance.getDefault().getTable("GRIP/Powercell");
  }

  @Override
  public void execute() {
    double[] xPositions = table.getEntry("x").getDoubleArray(defaultValue);
    double[] yPositions = table.getEntry("y").getDoubleArray(defaultValue);
    double[] sizes = table.getEntry("size").getDoubleArray(defaultValue);

    double closest = 0;
    int winner = -1;
    for(int i = 0; i < sizes.length; i++) {
      if (sizes[i] > closest) {
        closest = sizes[i];
        winner = i;
      }
    }

    

    if(winner != -1) {

      
      // There is a target
      double x = xPositions[winner];
      double y = yPositions[winner];
      double size = sizes[winner];

      System.out.println(x);
      
      double xSpeed = -0.3;
      double ySpeed = 0;

      if (x <= 80) {
        // sola dön
       ySpeed = -0.45;
      } else if (x > 80) {
        // sağa dön
        ySpeed = -0.45;
      }
      m_driveTrain.arcadeDrive(xSpeed, ySpeed);
      new PullCommand(m_pull, 0.3, 0).schedule();


    } else {
      m_driveTrain.arcadeDrive(0, 0);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
