package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Sara N on 1/13/2018.
 */

@TeleOp
public class TestColorSensor extends Generic_Drive {
    public void robotLoop() {

        telemetry.addData("Left Blue", JewelColorLeft.blue());
        telemetry.addData("Left Red", JewelColorLeft.red());
        telemetry.addData("Right Blue", JewelColorRight.blue());
        telemetry.addData("Right Red", JewelColorRight.red());
        telemetry.update();
    }

    @Override
    public void robotRun() {
        while (should_keep_running()) {
            robotLoop();
        }
    }

}
