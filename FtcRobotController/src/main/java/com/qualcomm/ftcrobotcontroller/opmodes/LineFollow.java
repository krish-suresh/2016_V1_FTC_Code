/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class LineFollow extends LinearOpMode {

    public enum ColorSensorDevice {ADAFRUIT, HITECHNIC_NXT, MODERN_ROBOTICS_I2C}

    ;

    public ColorSensorDevice device = ColorSensorDevice.MODERN_ROBOTICS_I2C;

    ColorSensor colorSensor;
    DeviceInterfaceModule cdim;
    LED led;
    TouchSensor t;
    DcMotor motorRight;
    DcMotor motorLeft;


    @Override
    public void runOpMode() throws InterruptedException {
        hardwareMap.logDevices();
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        switch (device) {
            case HITECHNIC_NXT:
                colorSensor = hardwareMap.colorSensor.get("nxt");
                break;
            case ADAFRUIT:
                colorSensor = hardwareMap.colorSensor.get("lady");
                break;
            case MODERN_ROBOTICS_I2C:
                colorSensor = hardwareMap.colorSensor.get("mr");
                break;
        }
        t = hardwareMap.touchSensor.get("t");

        waitForStart();
        motorRight.setPower(0);
        motorLeft.setPower(0);
        enableLed(false);
        while (opModeIsActive()) {
            enableLed(true);

            //Basiclinefollow();
            Advancedlinefollow();
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());

            waitOneFullHardwareCycle();
        }
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    private void enableLed(boolean value) {
        switch (device) {
            case HITECHNIC_NXT:
                colorSensor.enableLed(value);
                break;
            case ADAFRUIT:
                led.enable(value);
                break;
            case MODERN_ROBOTICS_I2C:
                colorSensor.enableLed(value);
                break;
        }
    }

    public void Basiclinefollow() {


            if (is_color_black() != true) {
                motorLeft.setPower(-0.3);
            }else {
                motorLeft.setPower(0);
            }
            if (is_color_white() != true) {
                motorRight.setPower(0.3);
            }else {
                motorRight.setPower(0);
            }
    }

    public void Advancedlinefollow() {
        double y = average_color();
        double motorLeftalf = y;
        double motorRightalf = 1 - y;


        motorRightalf = -motorRightalf;
        motorLeft.setPower(motorLeftalf / 12);
        motorRight.setPower(motorRightalf / 12);
        //motorLeft.setPower(.2);
        //motorRight.setPower(.2);
        telemetry.addData("The Average", y);
    }


    public boolean is_color_black() {
        int x = 25;
        if (colorSensor.red() <= x && colorSensor.blue() <= x && colorSensor.green() <= x) {
            return true;
        } else {
            return false;
        }
    }

    public double average_color() {
        double x;
        x = (colorSensor.blue() + colorSensor.green() + colorSensor.red()) / 3;
        x = x / 30;
        x = x - 1;
        return x;
    }

    public boolean is_color_white() {
        int x = 35;
        if (colorSensor.red() >= x && colorSensor.blue() >= x && colorSensor.green() >= x) {
            return true;
        } else {
            return false;
        }
    }
}