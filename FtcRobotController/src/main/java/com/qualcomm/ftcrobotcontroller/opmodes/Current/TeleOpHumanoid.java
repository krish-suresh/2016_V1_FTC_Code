// Humanoid project    DATE: 8/8/16 started


package com.qualcomm.ftcrobotcontroller.opmodes.Current;

import com.qualcomm.ftcrobotcontroller.opmodes.FTC_Default.PushBotHardware;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class TeleOpHumanoid extends PushBotHardware {

    //Drive

    double left = 0.0;
    double right = 0.0;


    DcMotor motorRight;
    DcMotor motorLeft;

    //Arm

    DcMotor motorRightArm;

    Servo servoRightArm;

    public TeleOpHumanoid() {
    }

    @Override
    public void init() {


        motorRight = hardwareMap.dcMotor.get("motor_1");
        motorLeft = hardwareMap.dcMotor.get("motor_2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);


        motorRightArm = hardwareMap.dcMotor.get("rightarm");
        motorRightArm.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        reset_drive_encoders();


        servoRightArm = hardwareMap.servo.get("servorightarm");
    }


    @Override
    public void loop() {
        /*
        if(gamepad1.left_stick_y > left){
            left = left + .01;
        }else if(gamepad1.left_stick_y < left){
            left = left - .01;
        }


        if(gamepad1.right_stick_y > right){
            right = right + .01;
        }else if(gamepad1.right_stick_y < right){
            right = right - .01;
        }
        */

        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;


        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);


        motorRight.setPower(scaleInput(right) / 2);
        motorLeft.setPower(scaleInput(left) / 2);


        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
        telemetry.addData("", motorRightArm.getCurrentPosition());
    }


    @Override
    public void stop() {

    }


    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
