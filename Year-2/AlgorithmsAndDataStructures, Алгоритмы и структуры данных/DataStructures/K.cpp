#include <bits/stdc++.h>

using namespace std;

struct MemorySegment {
  int index;
  int startPosition;
  int size;
  bool isAvailable;

  MemorySegment *previous;
  MemorySegment *next;

  MemorySegment(int index, int startPosition, int size, bool isAvailable,
                MemorySegment *previous, MemorySegment *next)
      : index(index), startPosition(startPosition), size(size),
        isAvailable(isAvailable), previous(previous), next(next) {
    if (previous) {
      previous->next = this;
    }
    if (next) {
      next->previous = this;
    }
  }

  void remove() {
    if (previous) {
      previous->next = next;
    }
    if (next) {
      next->previous = previous;
    }
  }
};

MemorySegment *memoryRequests[100000];
MemorySegment *freeMemorySegments[100000];

void swapSegments(int a, int b) {
  MemorySegment *temp = freeMemorySegments[a];
  freeMemorySegments[a] = freeMemorySegments[b];
  freeMemorySegments[b] = temp;
  freeMemorySegments[a]->index = a;
  freeMemorySegments[b]->index = b;
}

bool compareSegments(MemorySegment *a, MemorySegment *b) {
  return a->size < b->size;
}

int getParent(int i) { return (i - 1) / 2; }

int getLeft(int i) { return 2 * i + 1; }

int getRight(int i) { return 2 * i + 2; }

void siftdown(MemorySegment **heap, int n, int i) {
  int j = i;
  if (getLeft(i) < n && compareSegments(heap[j], heap[getLeft(i)])) {
    j = getLeft(i);
  }
  if (getRight(i) < n && compareSegments(heap[j], heap[getRight(i)])) {
    j = getRight(i);
  }
  if (i != j) {
    swapSegments(i, j);
    siftdown(heap, n, j);
  }
}

void siftup(MemorySegment **heap, int i) {
  while (i > 0 && compareSegments(heap[getParent(i)], heap[i])) {
    swapSegments(i, getParent(i));
    i = getParent(i);
  }
}

void removeFromHeap(MemorySegment **heap, int &n, int i) {
  swapSegments(i, n - 1);
  n--;
  if (i >= n) {
    return;
  }
  siftup(heap, i);
  siftdown(heap, n, i);
}

int main() {
  ifstream inputFile("input.txt");
  ofstream outputFile("output.txt");

  int memorySize, requestCount;
  inputFile >> memorySize >> requestCount;

  int currentFree = 0;
  MemorySegment firstSegment = {currentFree, 0,       memorySize,
                                true,        nullptr, nullptr};
  freeMemorySegments[currentFree++] = &firstSegment;

  for (int i = 0; i < requestCount; i++) {
    int memoryRequest;
    inputFile >> memoryRequest;
    if (memoryRequest > 0) {
      MemorySegment *largestSegment = freeMemorySegments[0];
      if (!currentFree || largestSegment->size < memoryRequest) {
        memoryRequests[i] = nullptr;
        outputFile << -1 << endl;
      } else {
        memoryRequests[i] =
            new MemorySegment(-1, largestSegment->startPosition, memoryRequest,
                              false, largestSegment->previous, largestSegment);
        outputFile << memoryRequests[i]->startPosition + 1 << endl;
        largestSegment->startPosition += memoryRequest;
        largestSegment->size -= memoryRequest;
        if (largestSegment->size > 0) {
          siftdown(freeMemorySegments, currentFree, largestSegment->index);
        } else {
          largestSegment->remove();
          removeFromHeap(freeMemorySegments, currentFree, 0);
        }
      }
    } else {
      memoryRequest = -memoryRequest;
      MemorySegment *segmentToFree = memoryRequests[memoryRequest - 1];
      if (segmentToFree == nullptr) {
        continue;
      }
      bool previousAvailable =
          segmentToFree->previous && segmentToFree->previous->isAvailable;
      bool nextAvailable =
          segmentToFree->next && segmentToFree->next->isAvailable;
      if (!previousAvailable && !nextAvailable) {
        segmentToFree->isAvailable = true;
        segmentToFree->index = currentFree;
        freeMemorySegments[currentFree] = segmentToFree;
        siftup(freeMemorySegments, currentFree++);
      } else if (!previousAvailable) {
        segmentToFree->next->startPosition = segmentToFree->startPosition;
        segmentToFree->next->size += segmentToFree->size;
        siftup(freeMemorySegments, segmentToFree->next->index);
        segmentToFree->remove();
      } else if (!nextAvailable) {
        segmentToFree->previous->size += segmentToFree->size;
        siftup(freeMemorySegments, segmentToFree->previous->index);
        segmentToFree->remove();
      } else {
        segmentToFree->previous->size +=
            segmentToFree->size + segmentToFree->next->size;
        siftup(freeMemorySegments, segmentToFree->previous->index);
        segmentToFree->remove();
        removeFromHeap(freeMemorySegments, currentFree,
                       segmentToFree->next->index);
        segmentToFree->next->remove();
      }
      memoryRequests[i] = nullptr;
    }
  }

  inputFile.close();
  outputFile.close();
}