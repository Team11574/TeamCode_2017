package us.ftcteam11574.teamcode2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous(name="Autonomous Jewel and Park and Glyph", group="Autonomous")
@SuppressWarnings("unused")
public class AutonomousJewel extends Generic_Drive {
    @Override
    public void robotRun() {
        final AllianceColor ac = check_alliance();
        StartingPosition sp = getStartingPosition(ac);

        info("Checking Vuforia VuMark...");
        RelicRecoveryVuMark vuMark = checkVuMarkVisible(5.0);

        info("Picking up the glyph...");
        raiseLeftJewel();
        raiseRightJewel();
        closeGrabber();
        waitForClaw();
        positionGrabberLift(3.0);
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
        drive_distance(DRIVE_FORWARD, 10.0, 0.5);
        drive_distance(DRIVE_FORWARD, 8.0, 0.25);
        stop_all_motors();

        if (ac == AllianceColor.Blue) {
            drive_distance(STRAFE_RIGHT, 8.0, 0.5);
        } else {
            drive_distance(STRAFE_LEFT, 8.0, 0.5);
        }

        if (sp == StartingPosition.North) {
            if (ac == AllianceColor.Blue) {
                drive_distance(STRAFE_RIGHT, 2.5, 0.5);
            } else {
                drive_distance(STRAFE_LEFT, 6.0, 0.5);
            }
        }
        stop_all_motors();

        if (sp == StartingPosition.South) {
            info("Positioning at the Cryptobox from the South Balancing Stone...");

            info("Driving forward to center of the Cryptobox");
            drive_distance(DRIVE_FORWARD, 10.0, 0.5);
            stop_all_motors();

            info("Turning to face the Cryptobox");
            if (ac == AllianceColor.Blue)
                drive_distance(TURN_LEFT, 18.0, 0.25);
            else
                drive_distance(TURN_RIGHT, 18.0, 0.25);
            stop_all_motors();

            info("Driving forward to the Cryptobox");
            drive_distance(DRIVE_FORWARD, 12.5, 0.5);
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
        drive_distance(TURN_RIGHT, 4.0, 0.5);
        drive_distance(DRIVE_FORWARD, 5.0, 0.5);
        drive_distance(TURN_LEFT, 4.0, 0.5);
        stop_all_motors();

        info("Backing up from the Glyph");
        drive_distance(DRIVE_BACKWARD, 3.5, 0.5);
        stop_all_motors();
    }
}