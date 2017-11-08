package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutonomousPark", group="Autonomous")
@SuppressWarnings("unused")
public class AutonomousPark extends Generic_Drive {
    @Override
    public void robotRun() {
        // Drive to park in front of Cryptobox.
        drive_distance(DRIVE_BACKWARD, 15.0, 0.8);
        stop_all_motors();
    }

}