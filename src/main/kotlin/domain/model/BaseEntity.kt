package com.www.domain.model

import java.time.OffsetDateTime

abstract class BaseEntity {
    var seq: Int = 0
    var createdAt: OffsetDateTime = OffsetDateTime.now()
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
}
