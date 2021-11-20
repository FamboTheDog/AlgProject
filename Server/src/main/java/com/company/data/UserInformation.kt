package com.company.data

import java.util.*

data class UserInformation(var id: UUID,
                           var userCommunication: UserCommunication,
                           var x: Float,
                           var y: Float,
                           var angle: Float,
                           var lastTimeShot: Long)