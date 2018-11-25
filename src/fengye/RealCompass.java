package fengye;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassMindSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import fengye.util.Helper;

import java.lang.Math;

 public class RealCompass {

	public static void main(String[] args) throws Exception {
		// let character = Character()
		// Character character = new Character();
		CompassMindSensor compass = new CompassMindSensor(SensorPort.S1);
		compass.resetCartesianZero();

		// flip the motor if you want to compass mode!
		NXTMotor motor = new NXTMotor(MotorPort.A);
		motor.resetTachoCount();
		motor.setPower(10);
		
		float lastDegree = compass.getDegrees();
		int ledDisplayInterval = 500;
		int loopTime = 5;
		while(!Button.ESCAPE.isDown()) {
			

			float degree = (int) (compass.getDegrees() / 255.0f * 360.0f);
			while (lastDegree - degree > 180.0f)
			{
				degree += 360.0f;
			}

			while (lastDegree - degree < -180.0f)
			{
				degree -= 360.0f;
			}
			lastDegree = degree;

			

			float tachometer = (float)motor.getTachoCount();

			float diff = degree - tachometer;
			float power = Helper.getPowerExponential(Math.abs(diff) / 360.0f, 0.75f);
			power = Math.max(1, power);
			if (diff > 0) {
				motor.setPower((int)power);
				motor.forward();
			}
			else if (diff < 0) {
				motor.setPower((int)power);
				motor.backward();
			}
			else {
				motor.stop();
			}

			

			ledDisplayInterval += loopTime;
			if (ledDisplayInterval >= 500)
			{
				LCD.clear();
				LCD.drawString("Compass:", 0, 0);
				LCD.drawInt((int)degree, 0, 1);
				LCD.drawString("Tachometer:", 0, 2);
				LCD.drawInt((int)tachometer, 0, 3);

				ledDisplayInterval = 0;
			}

			Thread.sleep(loopTime);
		}
	}	
}
