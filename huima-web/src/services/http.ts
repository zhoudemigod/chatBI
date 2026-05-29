import type { ApiResponse } from '@/types/dashboard';

export async function request<T>(input: RequestInfo, init?: RequestInit): Promise<T> {
  const response = await fetch(input, {
    headers: {
      'Content-Type': 'application/json',
      ...(init?.headers ?? {})
    },
    ...init
  });

  if (!response.ok) {
    throw new Error(`请求失败，状态码：${response.status}`);
  }

  const result = (await response.json()) as ApiResponse<T>;
  if (!result.success) {
    throw new Error(result.message || '服务调用失败');
  }

  return result.data;
}

