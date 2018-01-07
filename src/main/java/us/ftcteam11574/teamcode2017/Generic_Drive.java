package us.ftcteam11574.teamcode2017;


import android.support.annotation.NonNull;
import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Locale;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Generic_Drive extends LinearOpMode {
    // Tag to log messages to the Android log with.
    final public static String LOG_TAG = "FTC11574";

    public void info(String msg) {
        Log.i(LOG_TAG, msg);
    }

    enum StartingPosition {
        Unknown,
        North,
        South,
    }

    @NonNull
    public StartingPosition getStartingPosition(AllianceColor ac) {
        final LeftRight lr = check_LeftRight();
        StartingPosition sp;

        if (ac == AllianceColor.Blue && lr == LeftRight.Right)
            sp = StartingPosition.South;
        else if (ac == AllianceColor.Blue && lr == LeftRight.Left)
            sp = StartingPosition.North;
        else if (ac == AllianceColor.Red && lr == LeftRight.Right)
            sp = StartingPosition.North;
        else if (ac == AllianceColor.Red && lr == LeftRight.Left)
            sp = StartingPosition.South;
        else
            sp = StartingPosition.Unknown;
        return sp;
    }



    // An exception to throw to indicate that "Stop" was pressed (or fired
// automatically due to timer expiration). The robot should stop
// immediately to avoid penalty points or crashing.
public class StopImmediatelyException extends RuntimeException {
    public StopImmediatelyException() { super(); }
}


    // Number of encoder counts per wheel revolution.
    final private static int ENCODER_CPR = 1120;

    // Gear Ratio; 1:1 - Direct Drive.
    final private static double GEAR_RATIO = 1.0;

    // Diameter of the wheels, in inches.
    final private static double WHEEL_DIAMETER = 3.937;

    // Circumference of the wheels, in inches.
    final private static double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    // Conversion factor from centimeters to inches.
    final private static double CM_TO_INCH = 2.54;

    // The factor of slippage of wheels when strafing. Measured to be about 8%.
    final private static double STRAFE_SLIPPAGE_FACTOR = 1.08;

    public static final double CLAW_LEFT_OPEN_POSITION = 0.09;
    public static final double CLAW_RIGHT_OPEN_POSITION = 0.09;

    public static final double CLAW_LEFT_OPEN_PARTIALLY = 0.5;
    public static final double CLAW_RIGHT_OPEN_PARTIALLY = 0.5;

    public static final double CLAW_LEFT_CLOSED_POSITION = 0.7;
    public static final double CLAW_RIGHT_CLOSED_POSITION = 0.7;

    public static final double JEWEL_LEFT_ARM_UP = 0.0;
    public static final double JEWEL_RIGHT_ARM_UP = 0.0;

    public static final double JEWEL_LEFT_ARM_DOWN = 1.0;
    public static final double JEWEL_RIGHT_ARM_DOWN = 1.0;

    public static final int CLAW_MOVEMENT_TIME = 200;

    public static final int JEWEL_ARM_MOVEMENT_TIME = 500;

    public static final double LIFT_PINION_PITCH = 28.0;

    public static final double LIFT_RACK_PITCH = 10.0;

    public static final double LIFT_ENCODER_CPI = (LIFT_RACK_PITCH / LIFT_PINION_PITCH) * ENCODER_CPR;


    // Each of the colors we need to know about, only red and blue.
    public enum AllianceColor {
    Unknown,
    Red,
    Blue,
}
    // Each of the sides we need to know about
    public enum LeftRight {
        Unknown,
        Left,
        Right,
 }
    // Each of the motors on the robot.
    final public static int MOTOR_COUNT = 4;
    final public static int mFL = 0;
    final public static int mFR = 1;
    final public static int mBL = 2;
    final public static int mBR = 3;
    final public static String[] MOTOR_NAMES = {
            "mFL", "mFR", "mBL", "mBR"
    };


    // The direction that each motor on the robot is oriented. The right-side motors are mounted
    // backwards relative to the left side ones.
    final private static DcMotorSimple.Direction MOTOR_DIRECTIONS[] = {
            DcMotor.Direction.FORWARD, // mFL
            DcMotor.Direction.REVERSE, // mFR
            DcMotor.Direction.FORWARD, // mBL
            DcMotor.Direction.REVERSE, // mBR
    };
    // Each driving direction supported by driving functions.

    final public static int DRIVE_FORWARD  = 0;
    final public static int DRIVE_BACKWARD = 1;
    final public static int TURN_LEFT      = 2;
    final public static int TURN_RIGHT     = 3;
    final public static int STRAFE_LEFT    = 4;
    final public static int STRAFE_RIGHT   = 5;

    // This serves as both a matrix of the motor directions needed to drive in each direction
    // which is supported (via the sign, + or -) as well as a correction table to allow driving
    // straighter in each supported direction. Reduce the values here to slow the motors slightly
    // or increase the values to speed them up.
    final private static double DRIVE_DIRECTIONS[][] = {
            // mFL,  mFR,   mBL,   mBR
            { +1.00, +1.00, +1.00, +1.00 }, // DRIVE_FORWARD
            { -1.00, -1.00, -1.00, -1.00 }, // DRIVE_BACKWARD
            { -1.00, +1.00, -1.00, +1.00 }, // TURN_LEFT
            { +1.00, -1.00, +1.00, -1.00 }, // TURN_RIGHT
            { -0.97, +0.97, +1.00, -1.00 }, // STRAFE_LEFT
            { +1.00, -0.92, -0.92, +1.04 }, // STRAFE_RIGHT
    };
    // An array of DcMotors to represent all of the motors.
    DcMotor motor[];

    //
    DcMotor motorGrabberLift;

    // A variable for the left servo
    Servo servoGrabberLeft;

    // a variable for the right servo
    Servo servoGrabberRight;

    // a variable for the left jewel servo
    Servo servoJewelLeft;

    // a variable for the right jewel servo
    Servo servoJewelRight;

    // The chassis-mounted red/blue alliance switch for autonomous mode.
    DigitalChannel alliance_switch;

    //The LeftRight switch
    DigitalChannel Left_Right;

    // The gyro sensor.
  //  GyroSensor gyro;

    //The left color sensor on the jewel arm.
    ColorSensor JewelColorLeft;

    //The right color sensor on the jewel arm.
    ColorSensor JewelColorRight;

    // Convert a distance, in inches, into an encoder count, including a wheel slippage correction
    // factor.
    public int distance_to_count(double distance, double slippage) {
        return (int) (slippage * ENCODER_CPR * distance / WHEEL_CIRCUMFERENCE * GEAR_RATIO);
    }

    // Convert from inches per second to encoder counts per second.
    public int speed_ips_to_cps(double speed_ips) {
        // Counts per inch of actual wheel movement.
        double cpi = ENCODER_CPR / WHEEL_CIRCUMFERENCE / GEAR_RATIO;

        // Counts per second at speed_ips.
        double cps = cpi * speed_ips;

        return (int)cps;
    }

    // Convert from miles per hour to encoder counts per second.
    public int speed_mph_to_cps(double speed_mph) {
        // Convert from *miles* per hour to *inches* per hour.
        double speed_iph = speed_mph * (5280.0 * 12.0) ;

        // Convert from inches per *hour* to inches per *second*.
        double speed_ips = speed_iph * (60.0 * 60.0);

        // Call the above function to convert to counts per second.
        return speed_ips_to_cps(speed_ips);
    }

    // If stop was requested, throw a StopImmediatelyException which will be
    // caught by runOpMode to stop the robot immediately.
    public boolean should_keep_running() {
        if(isStarted() && isStopRequested())
            throw new Generic_Drive.StopImmediatelyException();
        return true;
    }


    public void set_mode_all_motors(DcMotor.RunMode mode) {
        for(int i=0; i < MOTOR_COUNT; i++) {
            if(motor[i].getMode() != mode) {
                motor[i].setMode(mode);
            }
        }
    }
    // Stop all motors immediately.
    public void stop_all_motors() {
        set_mode_all_motors(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void allow_control_all_motors() {
        set_mode_all_motors(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Set the power of a given motor.
    public void set_motor_power(int motor_index, int direction, double power) {
        motor[motor_index].setPower(power);
    }

    // Check if at least one motor is stopped (because it has reached its desired position).
    public boolean one_motor_stopped() {
        for(int i=0; i < MOTOR_COUNT; i++) {
            if(!motor[i].isBusy())
                return true;
        }
        return false;
    }

    // Wait for at least one motor to stop.
    public void wait_for_one_stop() {
        while(should_keep_running()) {
            if (one_motor_stopped())
                return;
        }
    }

    // Check if all motors are stopped (because they have all reached their desired positions).
    public boolean all_motors_stopped() {
        for(int i=0; i < MOTOR_COUNT; i++) {
            if(motor[i].isBusy())
                return false;
        }
        return true;
    }

    // Wait for all motors to stop.
    public void wait_for_all_stop() {
        while(should_keep_running()) {
            if (all_motors_stopped())
                return;
        }
    }

    // Check if at least one encoder has reached its desired position.
    public boolean one_encoder_satisfied() {
        for(int i=0; i < MOTOR_COUNT; i++) {
            // We're advancing forwards.
            if(motor[i].getPower() > 0.0 && motor[i].getCurrentPosition() >= motor[i].getTargetPosition())
                return true;

            // We're advancing backwards.
            if(motor[i].getPower() < 0.0 && motor[i].getCurrentPosition() <= motor[i].getTargetPosition())
                return true;
        }
        return false;
    }

    // Check if at least one encoder has reached its desired position.
    public boolean all_encoders_satisfied() {
        int encoders_satisfied = 0;
        for(int i=0; i < MOTOR_COUNT; i++) {
            if(motor[i].getPower() == 0.0)
                encoders_satisfied += 1;

            // We're advancing forwards.
            if(motor[i].getPower() > 0.0 && motor[i].getCurrentPosition() >= motor[i].getTargetPosition())
                encoders_satisfied += 1;

            // We're advancing backwards.
            if(motor[i].getPower() < 0.0 && motor[i].getCurrentPosition() <= motor[i].getTargetPosition())
                encoders_satisfied += 1;
        }
        return (encoders_satisfied == MOTOR_COUNT);
    }

    // Wait for at least one encoder to have reached its desired position.
    public void wait_for_one_encoder_satisfied() {
        while(should_keep_running()) {
            if (one_encoder_satisfied())
                return;
        }
    }

    // Wait for at least one encoder to have reached its desired position.
    public void wait_for_all_encoders_satisfied() {
        while(should_keep_running()) {
            if (all_encoders_satisfied())
                return;
        }
    }

    // Drive in a given direction at a given speed until at least one encoder reaches the
    // given count.
    public void drive_to_position(int direction, int count, double speed) {
        info(String.format(Locale.US, "Called drive_to_position: direction=%d, count=%d, speed=%.2f",
                direction, count, speed));

        for(int i=0; i < MOTOR_COUNT; i++) {
            if(motor[i].getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                motor[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            motor[i].setTargetPosition((int)((double)count * DRIVE_DIRECTIONS[direction][i]));
            set_motor_power(i, direction, 0.20 * speed * DRIVE_DIRECTIONS[direction][i]);
        }

        sleep(100);
        for(int i=0; i < MOTOR_COUNT; i++) {
            set_motor_power(i, direction, 0.50 * speed * DRIVE_DIRECTIONS[direction][i]);
        }

        sleep(30);
        for(int i=0; i < MOTOR_COUNT; i++) {
            set_motor_power(i, direction, 1.00 * speed * DRIVE_DIRECTIONS[direction][i]);
        }
    }

    // Drive at a constant speed
    public void drive_constant_speed(int direction, double speed) {
        info(String.format(Locale.US, "Called drive_constant_speed: direction=%d, speed=%.2f",
                direction, speed));


        for(int i=0; i < MOTOR_COUNT; i++) {
            if(motor[i].getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                motor[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            set_motor_power(i, direction, speed * DRIVE_DIRECTIONS[direction][i]);
        }
    }

    // Start driving in a given direction at a given speed for a maximum of the given distance,
    // but return immediately rather than waiting to reach the position.
    public void drive_distance_start(int direction, double distance, double speed) {
        double slippage = 1.0;
        if(direction == STRAFE_LEFT || direction == STRAFE_RIGHT)
            slippage = STRAFE_SLIPPAGE_FACTOR;
        for(int i=0; i < MOTOR_COUNT; i++) {
            int new_position = motor[i].getCurrentPosition();
            new_position += DRIVE_DIRECTIONS[direction][i] * distance_to_count(distance, slippage);
            // In constant-speed RUN_USING_ENCODER mode, the setTargetPosition is advisory
            // only and we'll check it ourselves against getCurrentPosition.
            motor[i].setTargetPosition(new_position);
        }
        drive_constant_speed(direction, speed);
    }

    // Drive in a given direction at a given speed until reaching the given distance.
    public void drive_distance(int direction, double distance, double speed) {
        drive_distance_start(direction, distance, speed);
        wait_for_one_encoder_satisfied();
    }

    public void openGrabber() {
        servoGrabberLeft.setPosition(CLAW_LEFT_OPEN_POSITION);
        servoGrabberRight.setPosition(CLAW_RIGHT_OPEN_POSITION);
    }

    public void closeGrabber() {
        servoGrabberLeft.setPosition(CLAW_LEFT_CLOSED_POSITION);
        servoGrabberRight.setPosition(CLAW_RIGHT_CLOSED_POSITION);
    }

    public void OpenClawPartially() {
        servoGrabberLeft.setPosition(CLAW_LEFT_OPEN_PARTIALLY);
        servoGrabberRight.setPosition(CLAW_RIGHT_OPEN_PARTIALLY);
    }
    public void lowerLeftJewel() {
        servoJewelLeft.setPosition(JEWEL_LEFT_ARM_DOWN);
    }

    public void raiseLeftJewel() {
        servoJewelLeft.setPosition(JEWEL_LEFT_ARM_UP);
    }

    public void lowerRightJewel() {
        servoJewelRight.setPosition(JEWEL_RIGHT_ARM_DOWN);
    }

    public void raiseRightJewel() {
        servoJewelRight.setPosition(JEWEL_RIGHT_ARM_UP);
    }

    private double previousGrabberLiftHeight = 0.0;
    private double targetGrabberLiftHeight = 0.0;

    public void positionGrabberLift(double height) {
        previousGrabberLiftHeight = targetGrabberLiftHeight;
        targetGrabberLiftHeight = height * LIFT_ENCODER_CPI;
        motorGrabberLift.setTargetPosition((int)(targetGrabberLiftHeight));
        motorGrabberLift.setPower(0.5);

    }

    public void waitForGrabberLift() {
        while (should_keep_running()) {
            telemetry.addData("Position...", motorGrabberLift.getCurrentPosition());
            telemetry.addData("Target...", motorGrabberLift.getTargetPosition());
            telemetry.update();
            if (previousGrabberLiftHeight < targetGrabberLiftHeight) { // moving up
                if (motorGrabberLift.getCurrentPosition() >= (motorGrabberLift.getTargetPosition()-40))
                    return;
            } else if (previousGrabberLiftHeight > targetGrabberLiftHeight) { //moving down
                if (motorGrabberLift.getCurrentPosition() <= (motorGrabberLift.getTargetPosition()+40))
                    return;
            }

        }
    }
    public void waitForJewelArm() {
        ElapsedTime elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (should_keep_running() && elapsedTime.time() < JEWEL_ARM_MOVEMENT_TIME);
    }

    public void waitForClaw() {
        ElapsedTime elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (should_keep_running() && elapsedTime.time() < CLAW_MOVEMENT_TIME);
    }

    public Generic_Drive.AllianceColor check_alliance() {
        if (alliance_switch.getState())
            return AllianceColor.Blue;
        else
            return AllianceColor.Red;
    }

    public Generic_Drive.LeftRight check_LeftRight() {
        if (Left_Right.getState())
            return LeftRight.Right;
        else
            return LeftRight.Left;
    }

    private int JEWEL_COLOR_SAMPLES = 10;
    public AllianceColor checkJewelColor(ColorSensor sensor) {
        int red = 0;
        int blue = 0;

        ElapsedTime elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        for (int i = 0; i < JEWEL_COLOR_SAMPLES; i++) {
            red += sensor.red();
            blue += sensor.blue();
            while (should_keep_running() && elapsedTime.time() < 10);
            elapsedTime.reset();
        }
        red /= JEWEL_COLOR_SAMPLES;
        blue /= JEWEL_COLOR_SAMPLES;

        info(String.format(Locale.US, "checkJewelColor: red = %d, blue = %d", red, blue));
        if (red < 5 || blue < 5)
            return AllianceColor.Unknown;
        else if (red > blue)
            return AllianceColor.Red;
        else if (blue > red)
            return AllianceColor.Blue;
        else
            return AllianceColor.Unknown;
    }

    // Initialize the robot and all its sensors.
    public void robotInit() {
        info("Initialization starting...");

        // Initialize all motors in a loop.
        info("* Initializing motors...");
        motor = new DcMotor[MOTOR_COUNT];
        for(int i=0; i < MOTOR_COUNT; i++) {
            motor[i] = hardwareMap.dcMotor.get(MOTOR_NAMES[i]);
            motor[i].setDirection(MOTOR_DIRECTIONS[i]);
            motor[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor[i].setPower(0.0);
        }
        // Initialize motorGrabberLift
        motorGrabberLift = hardwareMap.dcMotor.get("mLS");
        motorGrabberLift.setDirection(DcMotor.Direction.FORWARD);
        motorGrabberLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // Initialize grabber servos
        servoGrabberLeft = hardwareMap.servo.get("SL");
        servoGrabberRight = hardwareMap.servo.get("SR");
        servoGrabberLeft.setDirection(Servo.Direction.REVERSE);

        servoJewelRight = hardwareMap.servo.get("SJ1");
        servoJewelLeft = hardwareMap.servo.get("SJ2");
        servoJewelRight.setDirection(Servo.Direction.REVERSE);


        // Make sure everything starts out stopped.
        stop_all_motors();


        // Initialize the alliance switch.
        info("* Initializing alliance switch...");
        alliance_switch = hardwareMap.digitalChannel.get("alliance_switch");
        alliance_switch.setMode(DigitalChannelController.Mode.INPUT);

        // Initialize the left_right switch.
        info("* Initializing Left_Right switch...");
        Left_Right = hardwareMap.digitalChannel.get("left_right_switch");
        Left_Right.setMode(DigitalChannelController.Mode.INPUT);

        // Initialize the color sensor for the jewel arm.
        info("* Initializing the jewel color sensors...");
        JewelColorLeft = hardwareMap.colorSensor.get("jewel_color_left");
        JewelColorRight = hardwareMap.colorSensor.get("jewel_color_right");


        // Initialize the gyro.
       /* info("* Initializing gyro sensor...");
        gyro = hardwareMap.gyroSensor.get("gyro");
        telemetry.addData(">", "Calibrating gyro...");
        gyro.calibrate();
        while(gyro.isCalibrating() && !isStopRequested()){}
        info("Initialization complete.");
    */
    }


    public void robotWaitForStart() {
        while(!isStarted() && !isStopRequested()) {
            // Send some basic sensor data telemetry for confirmation and testing.
            telemetry.addData("1. alliance", check_alliance());
            //telemetry.addData("2. gyro", gyro.getHeading());
            telemetry.addData("3. LeftRight", check_LeftRight());
            telemetry.update();
        }
    }

    public void robotRun() {
        // Do nothing. This should be overridden in a subclass.
    }

    @Override
    public void runOpMode() {
        // The entire execution of the OpMode is inside a try block so that
        // any exceptions generated can be caught and a stack trace logged.
        try {
            info("Calling robotInit()...");
            robotInit();

            info("Calling robotWaitForStart()...");
            robotWaitForStart();

            // Exit immediately if stop was pressed, otherwise continue.
            if (!isStarted() || isStopRequested()) {
                info("Stop requested!");
                return;
            }

            info("Calling robotRun()...");
            robotRun();

            // Stop just in case robotRun did not.
            stop_all_motors();
        } catch (Throwable t) {
            // Expected due to timer expiration or "Stop" button pressed.
            if (t instanceof Generic_Drive.StopImmediatelyException) {
                info("Stop requested!");
                stop_all_motors();
                return;
            }

            // Unexpected exception; log it, and then re-throw a RuntimeException.
            Log.e(LOG_TAG, "Exception caught!", t);

            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }

            throw new RuntimeException(t);
        }
    }

}

