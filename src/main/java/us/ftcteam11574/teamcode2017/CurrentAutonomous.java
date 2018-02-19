package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous(name="Autonomous Jewel and Glyphs and Park", group="Autonomous")
@SuppressWarnings("unused")
public class CurrentAutonomous extends Generic_Drive {
    @Override
    public void robotRun() {
        final AllianceColor ac = check_alliance();
        StartingPosition sp = getStartingPosition(ac);

        info("Grabbing the glyph...");
        raiseLeftJewel();
        raiseRightJewel();
        closeGrabber();
        waitForClaw();

        // Tell the grabber to lift.
        positionGrabberLift(3.0);

        info("Checking Vuforia VuMark...");
        //It usually takes 3 seconds to read VuMark, 3.5 should be sufficient
        RelicRecoveryVuMark vuMark = checkVuMarkVisible(3.5);

        info("Waiting for grabber to lift...");
        waitForGrabberLift();

        int bumpDirection;
        int returnDirection;

        // Set the directions to turn to bump the jewel and return to straight.
        if (ac == AllianceColor.Blue) {
            info("Checking the left side jewel color...");
            lowerLeftJewel();
            waitForJewelArm();
            AllianceColor jewelColor = checkJewelColor(JewelColorLeft);
            info("Jewel is " + jewelColor + " Alliance is " + ac);
            if (jewelColor == ac) {
                bumpDirection = TURN_LEFT;
                returnDirection = TURN_RIGHT;
            } else {
                bumpDirection = TURN_RIGHT;
                returnDirection = TURN_LEFT;
            }
        } else {
            info("Checking the right side jewel color...");
            lowerRightJewel();
            waitForJewelArm();
            AllianceColor jewelColor = checkJewelColor(JewelColorRight);
            info("Jewel is " + jewelColor + " Alliance is " + ac);
            if (jewelColor != ac) {
                bumpDirection = TURN_LEFT;
                returnDirection = TURN_RIGHT;
            } else {
                bumpDirection = TURN_RIGHT;
                returnDirection = TURN_LEFT;
            }
        }

        info("Bumping off the jewel!");
        drive_distance(bumpDirection, 3.0, 0.2);
        stop_all_motors();

        info("Raising the jewel arm...");
        if (ac == AllianceColor.Blue) {
            raiseLeftJewel();
        } else {
            raiseRightJewel();
        }
        waitForJewelArm();

        info("Returning to hopefully straight!");
        drive_distance(returnDirection, 3.0, 0.2);
        stop_all_motors();

        info("Driving off the Balancing Stone");
        drive_distance(DRIVE_FORWARD, 5.0, 0.25);
        drive_distance(DRIVE_FORWARD, 12.0, 0.5);
        drive_distance(DRIVE_FORWARD, 5.0, 0.25);
        stop_all_motors();

        if (ac == AllianceColor.Blue) {
            drive_distance(STRAFE_RIGHT, 10.0, 0.5);
        } else {
            drive_distance(STRAFE_LEFT, 10.0, 0.5);
        }

        if (sp == StartingPosition.North) {
            if (ac == AllianceColor.Blue) {
                drive_distance(STRAFE_RIGHT, 4.0, 0.5);
            } else {
                drive_distance(STRAFE_LEFT, 6.0, 0.5);
            }
        }
        stop_all_motors();

        if (sp == StartingPosition.South) {
            info("Positioning at the Cryptobox from the South Balancing Stone...");

            info("Driving forward to center of the Cryptobox");
            drive_distance(DRIVE_FORWARD, 8.0, 0.5);
            stop_all_motors();

            info("Turning to face the Cryptobox");
            if (ac == AllianceColor.Blue)
                drive_distance(TURN_LEFT, 16.0, 0.25);
            else
                drive_distance(TURN_RIGHT, 16.0, 0.25);
            stop_all_motors();

            info("Driving forward to the Cryptobox");
            drive_distance(DRIVE_FORWARD, 8.5, 0.5);
            stop_all_motors();
        }

        info("Aligning with the Cryptobox key column");
        if (vuMark == RelicRecoveryVuMark.LEFT) {
            info("Aligning with the Cryptobox key column");
            drive_distance(STRAFE_LEFT, CRYPTOBOX_COLUMN_WIDTH, 0.25);
            stop_all_motors();
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            info("Aligning with the Cryptobox key column");
            drive_distance(STRAFE_RIGHT, CRYPTOBOX_COLUMN_WIDTH, 0.25);
            stop_all_motors();
        }
        info("Driving close to the Cryptobox");
        drive_distance(DRIVE_FORWARD, 5.5, .5);
        stop_all_motors();


        info("Dropping the Glyph off...");
        openGrabber();
        waitForClaw();
        positionGrabberLift(0.0);
        waitForGrabberLift();

        info("Pushing the Glyph into the Cryptobox");
        drive_distance(TURN_RIGHT, 2.0, 0.5);
        drive_distance(DRIVE_FORWARD, 5.0, 0.5);
        drive_distance(TURN_LEFT, 4.0, 0.5);

        info("Backing up from the Glyph");
        drive_distance(DRIVE_BACKWARD, 4.5, 0.5);
        stop_all_motors();

        if (sp == StartingPosition.North && ac == AllianceColor.Blue) {
            drive_distance(DRIVE_BACKWARD, 3.0, 0.5);
            drive_distance(STRAFE_RIGHT, 12.0, 0.5);
            drive_distance(TURN_RIGHT, 35.0, 0.5);
            OpenClawPartially();
            drive_distance(DRIVE_FORWARD, 67.0, 0.75);
            closeGrabber();
            positionGrabberLift(7.0);
            drive_distance(DRIVE_BACKWARD, 3, 0.5);
            drive_distance(TURN_LEFT, 40.0, 0.5);
            drive_distance(DRIVE_FORWARD, 64.0, 0.5);
            openGrabber();
            positionGrabberLift(0.0);
            drive_distance(DRIVE_FORWARD, 2.0, 0.5);
            drive_distance(DRIVE_BACKWARD, 2.0, 0.5);
            stop_all_motors();
        }
    }
}