#include <jni.h>
#include <string>
#include <android/log.h>
#include "asynccallback.h"

#define LOG_TAG "NativeBridge"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

struct CallbackContext {
    jobject callbackRef;
    JavaVM* jvm;
};

void jni_callback(const char* str_data, int str_length, void* context) {
    auto* ctx = static_cast<CallbackContext*>(context);
    JNIEnv* env = nullptr;
    ctx->jvm->AttachCurrentThread(&env, nullptr);

    jstring result = env->NewStringUTF(str_data);

    jclass cbClass = env->GetObjectClass(ctx->callbackRef);
    jmethodID onResult = env->GetMethodID(cbClass, "onResult", "(Ljava/lang/String;)V");

    env->CallVoidMethod(ctx->callbackRef, onResult, result);

    env->DeleteLocalRef(result);
    env->DeleteLocalRef(cbClass);
    env->DeleteGlobalRef(ctx->callbackRef);
    delete ctx;
}

extern "C" JNIEXPORT void JNICALL Java_ru_aroize_vegasoftapp_bridge_impl_NativeBridge_nativePing(
    JNIEnv* env,
    jobject,
    jstring input,
    jint delaySeconds,
    jobject callback
) {
    const char* str = env->GetStringUTFChars(input, nullptr);
    int length = env->GetStringUTFLength(input);

    auto* ctx = new CallbackContext();
    env->GetJavaVM(&ctx->jvm);
    ctx->callbackRef = env->NewGlobalRef(callback);

    async_callback_ping(str, length, delaySeconds, jni_callback, ctx);

    env->ReleaseStringUTFChars(input, str);
}
