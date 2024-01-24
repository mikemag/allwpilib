# WPILib for FTC

This is a fork of WPILib which targets Java 8 and is intended for use in FTC.

The goal is to enable full use of all of wpilib which doesn't depend on hardware. The Math and
Commands portions most notably.

WPILib doesn't work out-of-the box now for FTC for two main reasons:

* It targets Java 11, and FTC is locked to Java 8 due to the version of Android on the control hubs.
* It is specialized for Roborio hardware, and some broadly useful portions of the library need to
be modified to remove these dependencies.

## Current Status

* Builds for Java 8, all tests pass on one Macbook Pro.
* This has not yet been built into a FTC project or loaded onto a FTC control hub.
* No current promise of stability, or that it even works at all for FTC yet.

## Why

First, this is pretty frequently requested for a whole host of reasons, often around the nice math 
libraries, or the Command library. The usual answer is "go copy that from WPILib". 
Other libraries, like FTCLib, have ported interesting portions but are incomplete and out-of-date.

Second, it's my guess that the new shared FRC/FTC control system in 2027 will use WPILib. I'd like
to see our FTC teams get started using more of the current features sooner rather than later.

## Goals

In rough priority order:

* Ensure this is easily maintainable, and that we can pull from upstream any time we wish.
* Use the current WPILib Commands and Subsystems instead of FTCLib. This gives us full access to the latest hotness, and the ability to advance to the things you see here: https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html
* Use all of WPILib's math package. PIDs, filters, controllers, geometry, etc. An end to copying things from WPILib.
* Enable perfect sharing of code between FTC and FRC in team libraries.
* Can we drive in auto with WPILib on FTC?
* Can we hook up WPILib's telemetry to FTCDashboard?

## How

WPILib uses Java 11+ syntax, and targets Java 11 bytecode. [Jabel](https://github.com/bsideup/jabel) 
gets us around the bulk of the syntax issues, but there are still new or changed APIs not available 
in Java 8. These are handled with targeted code changes, made by hand, which turned out to be 
relatively small.

## Next Steps

Short-term (weeks) next steps I have planned:

* Get wpimath working on a FTC bot. Good proof-of-concept with fewer dependencies.
* Get wpiibNewCommands working on a FTC bot.
  * Will require us to break some current dependencies on wpilibj and the hardware layer. These look small so far.
* Pull from upstream to bring us up-to-date with the 2024 season library.

## Long-term Plan

The current plan is to maintain this until the new FIRST control system becomes available, likely
in 2026-27.

## Random Notes

* I've taken the approach of getting this to build for Java 8 with Jabel and hand modifications where necessary.
**There might be alternatives, though, which I haven't explored**:
  * Android Studio includes desugaring support with implementations of missing APIs. 
    * https://developer.android.com/studio/write/java11-default-support-table
    * If we build this in Android Studio with `coreLibraryDesugaring`, could we revert many of the API changes I've already made?
  * Can we install a newer JVM on the control hub?
    * My assumption is "no". We don't typically attempt to control the launching of the FTCRobotController app, or otherwise interfere with the app lifecycle given by FTC.
    * Using libraries that work like normal code, respecting the existing constraints of the system feels like the safest approach.
    * I also honestly have no idea how that would work on Android, either!
* I have notes on the deps from Commands to the rest of the lib somewhere. I need to dig them up and paste them here.
  * My goal is to break these deps and let Commands stand alone as much as possible. This will lower the surface we need to support.
