package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Sara N on 1/13/2018.
 */

@Autonomous
public class TestDrive extends Generic_Drive {
    @Override
    public void robotRun() {
        closeGrabber();
        waitForClaw();
        raiseLeftJewel();
        raiseRightJewel();
        drive_distance(DRIVE_FORWARD, 60.0, 0.5);
        stop_all_motors();
    }

}