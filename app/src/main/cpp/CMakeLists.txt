cmake_minimum_required(VERSION 3.10.2)

project("asynccallback")

include_directories(${CMAKE_SOURCE_DIR})

add_library(
    native-lib
    SHARED
        native-lib.cpp
        asynccallback.cpp
)

find_library(log-lib log)

target_link_libraries(
    native-lib
    ${log-lib}
)
