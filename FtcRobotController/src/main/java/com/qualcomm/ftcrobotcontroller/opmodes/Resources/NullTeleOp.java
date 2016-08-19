//Blank TeleOp mode to copy when starting a new TeleOp

package com.qualcomm.ftcrobotcontroller.opmodes.Resources;


public class NullTeleOp extends TeleOpImport {


    public NullTeleOp() {
    }

    @Override
    public void init() {

        addMotor("motor_1");
    }


    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }

}
