import { BaseException } from '../base.exception';
import { HttpStatus } from '@nestjs/common';

export class NotSupportedLanguageException extends BaseException {
  constructor(runtime: number) {
    super(
      `실행 시간을 초과하였습니다.\n실행 시간: ${runtime}`,
      HttpStatus.REQUEST_TIMEOUT,
      {
        runtime,
      },
    );
  }
}
