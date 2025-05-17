#include <string>
#include <thread>
#include <iostream>

#include "asynccallback.h"

void async_callback_ping(
    const char* str_data,
    int str_length,
    int delay,
    async_callback callback,
    void* callback_context
)
{
    std::string str(str_data,str_length);
    auto handler=[callback,callback_context,delay,str]()
    {
        if (delay!=0)
        {
            std::cout << "Sleeping for " << delay << " seconds" << std::endl;
            auto sleepSecs=std::chrono::seconds(delay);
            std::this_thread::sleep_for(sleepSecs);
        }
        callback(str.data(),static_cast<int>(str.size()),callback_context);
    };

    std::thread t(std::move(handler));
    t.detach();
}
