package com.w2m.spaceshiptask.source.service;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;

public interface SourceService {

    Source updateSource(Long id, SourceUpdateForm sourceForm) throws NotFoundException;


}
