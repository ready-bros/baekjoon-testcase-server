import { CodeRunnerResult } from '../util/constant';
import { NotSupportedLanguageException } from '../exception/domain/timeout.exception';

export interface RunningLanguageConstructor {
  new (runtime: number, code: string, input: string): RunningLanguage;
}

export abstract class RunningLanguage {
  runtimeLimit: number;

  abstract runCode(): Promise<CodeRunnerResult>;
  abstract calculateTimeLimit(runtime: number): number;

  protected handleTimeout(reject: any, runtime: number) {
    setTimeout(() => {
      reject(new NotSupportedLanguageException(Date.now() - runtime));
    }, this.runtimeLimit);
  }
}
