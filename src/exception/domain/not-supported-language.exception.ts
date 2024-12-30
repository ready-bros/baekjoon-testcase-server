import { BaseException } from '../base.exception';
import { HttpStatus } from '@nestjs/common';

export class NotSupportedLanguageException extends BaseException {
  constructor(language: string) {
    super(`지원되지 않는 언어입니다: ${language}`, HttpStatus.BAD_REQUEST, {
      language,
    });
  }
}
