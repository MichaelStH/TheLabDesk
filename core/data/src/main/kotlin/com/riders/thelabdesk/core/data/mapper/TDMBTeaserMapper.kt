package com.riders.thelabdesk.core.data.mapper

import com.riders.thelabdesk.core.data.local.model.tmdb.TDMBTeaserModel
import com.riders.thelabdesk.core.domain.entities.tmdb.TeaserEntity


fun TDMBTeaserModel.toEntity(): TeaserEntity = TeaserEntity(this.id, this.name, this.youtubeKey)