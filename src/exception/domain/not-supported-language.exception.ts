import { BaseException } from '../base.exception';
import { HttpStatus } from '@nestjs/common';

export class NotSupportedLanguageException extends BaseException {
  constructor(language: string) {
    super(`Language ${language} is not supported`, HttpStatus.BAD_REQUEST, {
      language,
    });
  }
}
