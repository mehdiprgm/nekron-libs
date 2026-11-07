#include "headers/WindowsTerminalController.h"

#include <conio.h>
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

extern "C" {

JNIEXPORT void JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_nativeClearScreen(
    JNIEnv* env, jobject obj) {
  system("cls");
}

JNIEXPORT void JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_nativeGotoxy(
    JNIEnv* env, jobject obj, jint x, jint y) {
  COORD coord;
  coord.X = (SHORT)x;
  coord.Y = (SHORT)y;
  SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), coord);
}

JNIEXPORT jint JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_nativeReadKey(
    JNIEnv* env, jobject obj, jboolean show) {
  if (show) {
    return getche();
  }

  return getch();
}

JNIEXPORT jint JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_getCursorXPosition(
    JNIEnv* env, jobject obj) {
  CONSOLE_SCREEN_BUFFER_INFO csbi;
  GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi);

  return csbi.dwCursorPosition.X;
}

JNIEXPORT jint JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_getCursorYPosition(
    JNIEnv* env, jobject obj) {
  CONSOLE_SCREEN_BUFFER_INFO csbi;
  GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi);

  return csbi.dwCursorPosition.Y;
}

JNIEXPORT jint JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_getWindowWidth(
    JNIEnv* env, jobject obj) {
  CONSOLE_SCREEN_BUFFER_INFO csbi;
  if (GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi)) {
    return csbi.srWindow.Right - csbi.srWindow.Left + 1;
  }

  return -1;
}

JNIEXPORT jint JNICALL
Java_com_emperor_nekron_lib_terminal_natives_LinuxTerminalController_getWindowHeight(
    JNIEnv* env, jobject obj) {
  CONSOLE_SCREEN_BUFFER_INFO csbi;
  if (GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi)) {
    return csbi.srWindow.Right - csbi.srWindow.Top + 1;
  }

  return -1;
}
}