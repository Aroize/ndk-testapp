#ifndef ASYNCCALLBACK_H
#define ASYNCCALLBACK_H

#ifdef __cplusplus
extern "C" {
#endif

typedef void async_callback (
    const char* str_data,
    int str_length,
    void* context
);

void async_callback_ping(
    const char* str_data,
    int str_length,
    int delay,
    async_callback callback,
    void* callback_context
);

#ifdef __cplusplus
} // extern "C"
#endif

#endif
