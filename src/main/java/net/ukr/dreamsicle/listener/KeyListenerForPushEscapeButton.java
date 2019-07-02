package net.ukr.dreamsicle.listener;


import net.ukr.dreamsicle.App;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * JNativeHook - библиотека, обеспечивающая глобальное прослушивание клавиатуры и мыши для Java.
 * Позволяеи прослушивать нажатие клавиатурв или движения мыши, которые в противном случае были
 * бы невозможны при использовании чистой Java. Чтобы выполнить эту задачу, JNativeHook использует
 * зависимый от платформы собственный код через собственный интерфейс Java для создания низкоуровневых
 * общесистемных хуков и доставки этих событий в приложение {@Link App}
 */
public class KeyListenerForPushEscapeButton implements NativeKeyListener {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(KeyListenerForPushEscapeButton.class);

    public void getKey() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            LOGGER.info("There was a problem registering the native hook.", ex);
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new KeyListenerForPushEscapeButton());
    }

    public void getUnregisterNativeHook() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            LOGGER.info("Problem with unregisterNativeHook", e);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                App.isRunning = false;
            } catch (NativeHookException ex) {
                LOGGER.info("Problem with unregisterNativeHook", ex);
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}