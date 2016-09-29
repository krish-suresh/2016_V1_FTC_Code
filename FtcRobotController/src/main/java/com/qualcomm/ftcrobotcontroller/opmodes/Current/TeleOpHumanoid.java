// Humanoid project    DATE: 8/8/16 started


package com.qualcomm.ftcrobotcontroller.opmodes.Current;

import com.qualcomm.ftcrobotcontroller.opmodes.FTC_Default.PushBotHardware;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class TeleOpHumanoid extends PushBotHardware {

    //Drive

    double leftF = 0.0;
    double rightF = 0.0;
    double leftB = 0.0;
    double rightB = 0.0;


    DcMotor motorFRight;
    DcMotor motorFLeft;

    DcMotor motorBRight;
    DcMotor motorBLeft;

    Servo servoRightArm;
    double servorightarmvalue = .5;

    Servo servoLeftArm;
    double servoleftarmvalue = .5;

    Servo servoHead;
    double servoheadvalue = .5;

    public TeleOpHumanoid() {
    }

    @Override
    public void init() {


        motorFRight = hardwareMap.dcMotor.get("motor_RF");
        motorFLeft = hardwareMap.dcMotor.get("motor_LF");
        motorBRight = hardwareMap.dcMotor.get("motor_RB");
        motorBLeft = hardwareMap.dcMotor.get("motor_LB");

        motorFRight.setDirection(DcMotor.Direction.REVERSE);
        motorBLeft.setDirection(DcMotor.Direction.REVERSE);


        //servoLeftArm = hardwareMap.servo.get("servoleftarm");


        //servoRightArm = hardwareMap.servo.get("servorightarm");


        servoHead = hardwareMap.servo.get("servohead");
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


        leftF = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
        rightF = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
        leftB = -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
        rightB = -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;


        motorFRight.setPower(scaleInput(leftF / 2));
        motorFLeft.setPower(scaleInput(rightF / 2));
        motorBRight.setPower(scaleInput(rightB / 2));
        motorBLeft.setPower(scaleInput(rightB / 2))
        ;


        if (gamepad1.right_bumper && !gamepad1.left_bumper) {
            servoheadvalue = servoheadvalue + .01;
        } else if (gamepad1.left_bumper && !gamepad1.right_bumper) {
            servoheadvalue = servoheadvalue - .01;
        }

        if (servoheadvalue > 1) {
            servoheadvalue = .5;
        } else if (servoheadvalue < 0) {
            servoheadvalue = 0;
        }

        servoHead.setPosition(servoheadvalue);


        telemetry.addData("Text", "*** Robot Data***");
        //telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
        //telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
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
