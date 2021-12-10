// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
public final class Constants {
	public static final int JOYSTICK = 0;
	public static class Ports {
		public static class Drive {
			public static final int LEFT = 0;
			public static final int RIGHT = 1;
		}
		public static class Pull {
			public static final int LEFT = 4;
			public static final int RIGHT = 5;
		}
		public static class Climb {
			public static final int LEFT = 2;
			public static final int RIGHT = 3;
		}
		public static class Shoot {
			public static final int LEFT = 6;
			public static final int RIGHT = 7;
		}
	}

	public static class JoystickButtons {
		public static class Pull {
			public static final int NORMAL = 3;
			public static final int REVERSE = 5;
			
			public static final int NORMAL_FAST = 8;
			public static final int REVERSE_FAST = 7;

			public static final int PREVENT_JAM = 10;
		}

		public static class Climb {
			public static final int NORMAL = 4;
			public static final int SLOW = 6;
			public static final int RESET = 12;
		}

		public static class Shoot {
			public static final int NORMAL = 1;
		}
	}
}
