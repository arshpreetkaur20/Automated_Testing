*** Settings ***
Library           SeleniumLibrary

*** Variables ***
${url}            C:/Users/Dell/Desktop/4th/Testing/Testing%20Assignment/Assignment%203/Software-Testing-Assi3-Frontend/todo.html
${browser}        edge
${text}           Note1
${search}         xpath=/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[1]
${Desc}           you have to do
${empty}          \

*** Test Cases ***
Add ToDo
    [Tags]    1
    [Setup]    open browser    ${url}    ${browser}
    Input Text    id=todo    ${text}
    Input Text    id=desc    ${Desc}
    sleep    1
    Click Element    xpath=//*[@id="todo-form"]/button
    sleep    2
    Current Frame Should Contain    ${text}
    sleep    0.5
    Element Should Contain    id=todo    ${empty}
    Element Should Contain    id=desc    ${empty}
    sleep    1
    [Teardown]    close browser

Delete ToDo
    [Tags]    2
    [Setup]    open browser    ${url}    ${browser}
    ${txt}=    Get WebElement    //*[@id="row-1"]/td[2]
    ${txt}=    Get Text    ${txt}
    Click Element    xpath=//*[@id="row-1"]/td[5]/button
    sleep    2
    Element Should Not Contain    id=todo-table    ${txt}
    sleep    2
    [Teardown]    close browser

Update TODo
    [Tags]    3
    [Setup]    open browser    ${url}    ${browser}
    Select Checkbox    id=checkbox-2
    sleep    2
    Checkbox Should Be Selected    id=checkbox-2
    sleep    5
    [Teardown]    close browser
