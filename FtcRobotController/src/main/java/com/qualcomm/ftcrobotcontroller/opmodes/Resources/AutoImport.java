package com.qualcomm.ftcrobotcontroller.opmodes.Resources;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.ftcrobotcontroller.opmodes.SensorClasses.*;
import com.qualcomm.ftcrobotcontroller.opmodes.FTC_Default.*;


public class AutoImport extends LinearOpMode

{
    DcMotor motorRight;
    DcMotor motorLeft;

    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            waitOneFullHardwareCycle();
        }
    }

    public double distance_to_rotations(double wheelSize, double distance) {
        return distance / circumference_of_wheel(wheelSize);
    }

    public void drive(double distance, DcMotor motorLeft, DcMotor motorRight, double power, double wheelSize) {
        //Drive with the encoders
        //Parameters are number of rotations
        //Measurements are in inches
        double Rotations = distance_to_rotations(wheelSize, distance);
        double encoder_target_value = Rotations * 2880;
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setPower(power);
        motorRight.setPower(power);
        motorLeft.setTargetPosition((int) -encoder_target_value);
        motorRight.setTargetPosition((int) encoder_target_value);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void drive_WithoutEnc(double distance, DcMotor motorLeft, DcMotor motorRight, double power) {

    }

    public double circumference_of_wheel(double Diameter) {
        final double pi = 3.14;
        return pi * Diameter;
    }

}
