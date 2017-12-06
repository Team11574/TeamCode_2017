package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Sara N on 12/1/2017.
 */

@Autonomous(name = "Autonomous Glyph And Park")
public class AutonomousGlyphAndPark extends Generic_Drive {

    public void robotRun() {
        // 1. Close Grabber
        closeGrabber();
        sleep(CLAW_MOVEMENT_TIME);
        // 2. Pick-up Glyph
        positionGrabberLift(6.0);
        waitForGrabberLift();
        // 3. Drive to Cryptobox
        final AllianceColor ac = check_alliance();
        StartingPosition sp = getStartingPosition(ac);

        if (sp == StartingPosition.South) {
            int strafe_direction;
            if (ac == AllianceColor.Blue)
                strafe_direction = STRAFE_LEFT;
            else
                strafe_direction = STRAFE_RIGHT;
            // Drive to park in front of Cryptobox.
            drive_distance(DRIVE_FORWARD, 6.0, 0.25);
            drive_distance(DRIVE_FORWARD, 24.0, 0.5);
            stop_all_motors();
            drive_distance(strafe_direction, 11.0, 0.5);
            stop_all_motors();
        } else if (sp == StartingPosition.North) {
            int strafe_direction;
            if (ac == AllianceColor.Blue)
                strafe_direction = STRAFE_RIGHT;
            else
                strafe_direction = STRAFE_LEFT;
            // Drive to park in front of Cryptobox.
            drive_distance(DRIVE_FORWARD, 18.0, 0.5);
            stop_all_motors();
            drive_distance(strafe_direction, 10.0, 0.5);
            stop_all_motors();
            drive_distance(DRIVE_FORWARD, 5.5, .5);
            stop_all_motors();
        }
        // 4. Spin to face Cryptobox
        //    -- Spin direction different for N/S or Blue vs Red?
        // 5. Drive forward
        // 6. Drop Glyph
        // 7. Back-up
    }
}
