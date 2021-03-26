#!/bin/sh

gradle_compile_release()
{
    echo "build release start!"
    ./gradlew :app:clean
    ./gradlew :app:assembleRelease

    rm -rf out/
    mkdir -p out/apk
    cp app/build/outputs/apk/release/app-release.apk out/apk/app-release.apk
    adb install -r out/apk/app-release.apk
    adb shell am start -n com.study.reviewpager/com.study.reviewpager.MainActivity
    echo "build release end!"
}

gradle_compile_debug()
{
    echo "build debug start!"
    ./gradlew :app:clean
    ./gradlew :app:assembleDebug

    rm -rf out/
    mkdir -p out/apk
    cp app/build/outputs/apk/debug/app-debug.apk out/apk/app-debug.apk
    adb install -r out/apk/app-debug.apk
    adb shell am start -n com.study.reviewpager/com.study.reviewpager.MainActivity
    echo "build debug end!"
}

Main()
{
    if [ "$1" == "0" ]; then
       gradle_compile_debug
    elif [ "$1" == "1" ]; then
       gradle_compile_release
    fi

    return $?
}

Main "$@"
