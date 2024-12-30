import { RunningLanguage } from './running-language';
import { CodeRunnerResult } from '../util/constant';
import { spawn, ChildProcess } from 'child_process';

export class Javascript extends RunningLanguage {
  runtimeLimit: number;
  code: string;
  input: string;

  constructor(runtime: number, code: string, input: string) {
    super();
    this.runtimeLimit = this.calculateTimeLimit(runtime);
    this.code = code;
    this.input = input;
  }

  runCode(): Promise<CodeRunnerResult> {
    return new Promise((resolve, reject) => {
      const child = this.createChildProcess();

      this.handleChildProcess(child, resolve, reject);
    });
  }

  createChildProcess() {
    return spawn('node', ['-e', this.code], {
      stdio: ['pipe', 'pipe', 'pipe'],
    });
  }

  handleChildProcess(child: ChildProcess, resolve: any, reject: any) {
    let output = '';
    const runtime = Date.now();

    child.stdout.on('data', (data: string) => {
      output += data.toString();
    });

    child.on('close', () => {
      resolve({ output, runtime: Date.now() - runtime });
    });

    this.handleError(child);
    this.handleTimeout(reject);

    child.stdin.write(this.input + '\n');
    child.stdin.end();
  }

  handleError(child: ChildProcess) {
    child.stderr.on('data', (data: string) => {
      console.error(`stderr: ${data}`);
    });
  }

  calculateTimeLimit(runtime: number): number {
    return runtime * 1000 * 3 + 2;
  }
}
